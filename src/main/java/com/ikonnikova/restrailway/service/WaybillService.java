package com.ikonnikova.restrailway.service;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.Waybill;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.repository.WaybillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;


@Service
public class WaybillService {

    @Autowired
    private WaybillRepository waybillRepository;

    @Autowired
    WagonRepository wagonRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Method createWaybill:
     * Receives a cargoId parameter, and creates a new waybill with the specified cargo.
     *
     * @param
     */

    public Waybill createWaybill(Waybill waybill) {
        return waybillRepository.save(waybill);
    }

    /**
     * Операция убытия вагонов на сеть РЖД.
     * Вагоны могут убывать только с начала состава.
     * <p>
     * Алгоритм действий:
     * Получаем 1ый вагон в накладной с помощью метода findFirstWagonByWaybill
     * Проверка, есть ли у первого вагона станционный путь, используя метод getStationTrack.
     * Получаем все вагоны в накладной с помощью метода findAllWagonsByWaybill
     * Сортируем вагоны по положению, используя метод сортировки и Comparator, который сравнивает вагоны по их положению
     * Проверка, находится ли 1ый вагон в начале поезда, сравнив его с первым вагоном в отсортированном списке.
     * Если 1ый вагон стоит в начале состава, то удаляем все вагоны из накладной методом deleteAllByWaybill.
     *
     * @param waybillId
     */
    @Transactional
    public void departWagons(Long waybillId) {
        Waybill waybill = waybillRepository.findById(waybillId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid waybill ID"));

        // Получаем 1ый вагон в накладной
        Wagon firstWagon = wagonRepository.findFirstWagonByWaybill(waybill);
        if (firstWagon == null) {
            throw new IllegalStateException("No wagons found in waybill");
        }

        // Проверка, стоит ли первый вагон в начале поезда
        StationTrack stationTrack = firstWagon.getStationTrack();
        if (stationTrack == null) {
            throw new IllegalStateException("Wagon " + firstWagon.getNumber() + " does not have a station track");
        }

        // Получаем все вагоны в накладной с помощью метода findAllWagonsByWaybill
        List<Wagon> wagons = wagonRepository.findAllWagonsByWaybill(waybill);

        // Сортируем вагоны по положению
        wagons.sort(Comparator.comparing(Wagon::getPosition));

        // Проверка, находится ли 1ый вагон в начале поезда, сравниваем его с первым вагоном в отсортированном списке.
        if (!wagons.get(0).equals(firstWagon)) {
            throw new IllegalStateException("Wagons can only be departed from the beginning of the train");
        }

        // Удаляем все вагоны из накладной методом deleteAllByWaybill, если 1ый вагон стоит в начале состава
        wagonRepository.deleteAllByWaybill(waybill);
    }

}







