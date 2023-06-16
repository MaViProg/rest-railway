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


/**
 * Сервис для работы с натурными листами для приема вагонов
 */
@Service
public class WaybillService {

    @Autowired
    private WaybillRepository waybillRepository;

    @Autowired
    private WagonRepository wagonRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Метод createWaybill:
     * Принимает параметр cargoId и создает новую накладную с указанным грузом.
     *
     * @param waybill создаваемая накладная
     * @return созданная накладная
     */
    public Waybill createWaybill(Waybill waybill) {
        return waybillRepository.save(waybill);
    }

    /**
     * Операция убытия вагонов на сеть РЖД.
     * Вагоны могут убывать только с начала состава.
     * <p>
     * Алгоритм действий:
     * 1. Получаем первый вагон в накладной с помощью метода findFirstWagonByWaybill.
     * 2. Проверяем, есть ли у первого вагона станционный путь, используя метод getStationTrack.
     * 3. Получаем все вагоны в накладной с помощью метода findAllWagonsByWaybill.
     * 4. Сортируем вагоны по положению с использованием метода сортировки и Comparator, который сравнивает вагоны по их положению.
     * 5. Проверяем, находится ли первый вагон в начале поезда, сравнив его с первым вагоном в отсортированном списке.
     * 6. Если первый вагон стоит в начале состава, удаляем все вагоны из накладной методом deleteAllByWaybill.
     *
     * @param waybillId идентификатор накладной
     */
    @Transactional
    public void departWagons(Long waybillId) {
        Waybill waybill = waybillRepository.findById(waybillId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid waybill ID"));

        // Получаем первый вагон в накладной
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

        // Проверка, находится ли первый вагон в начале поезда, сравниваем его с первым вагоном в отсортированном списке.
        if (!wagons.get(0).equals(firstWagon)) {
            throw new IllegalStateException("Wagons can only be departed from the beginning of the train");
        }

        // Удаляем все вагоны из накладной методом deleteAllByWaybill, если первый вагон стоит в начале состава
        wagonRepository.deleteAllByWaybill(waybill);
    }

}







