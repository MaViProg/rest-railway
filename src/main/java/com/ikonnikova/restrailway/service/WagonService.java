package com.ikonnikova.restrailway.service;

import com.ikonnikova.restrailway.entity.StationTrack;
import com.ikonnikova.restrailway.entity.Wagon;
import com.ikonnikova.restrailway.entity.WagonMovePosition;
import com.ikonnikova.restrailway.repository.StationTrackRepository;
import com.ikonnikova.restrailway.repository.WagonRepository;
import com.ikonnikova.restrailway.repository.WaybillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с вагонами.
 */
@Service
public class WagonService {

    @Autowired
    private WagonRepository wagonRepository;

    @Autowired
    private StationTrackRepository stationTrackRepository;

    @Autowired
    private WaybillRepository waybillRepository;

    /**
     * Операция приема вагонов на предприятие.
     * На входе список вагонов с учетом на какой путь станции данные вагоны принимаются.
     * Вагоны могут приниматься только в конец состава.
     *
     * @param wagonsId       список идентификаторов вагонов
     * @param stationTrackId идентификатор пути станции
     */
    public void receiveWagons(List<Long> wagonsId, Long stationTrackId) {

        // Получаем путь станции, на которую будут приняты вагоны
        StationTrack stationTrack = stationTrackRepository.findById(stationTrackId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid station track ID"));

        // Получаем последний вагон на данном пути станции, сортируя по позиции в обратном порядке
        Wagon lastWagon = wagonRepository.findTopByStationTrackOrderByPositionDesc(stationTrack);

        // Инициализируем переменную позиции вагона
        int position = lastWagon != null ? lastWagon.getPosition() : 1;

        // Перебираем каждый вагон из списка идентификаторов вагонов
        for (Wagon wagon : wagonRepository.findAllById(wagonsId)) {

            // Устанавливаем путь станции для вагона
            wagon.setStationTrack(stationTrack);

            // Устанавливаем позицию вагона в зависимости от значения переменной позиции
            wagon.setPosition(position == 0 ? WagonMovePosition.HEAD : WagonMovePosition.TAIL);

            // Сохраняем вагон в репозитории
            wagonRepository.save(wagon);

            // Обновляем значение переменной позиции на 0 для следующего вагона
            position = 0;
        }
    }


    /**
     * Операция перестановки вагонов внутри станции.
     * На входе список вагонов и путь на который они будут перемещены.
     * Вагоны могут быть перемещены только в начало или конец состава.
     *
     * @param wagons         список идентификаторов вагонов
     * @param stationTrackId идентификатор пути станции
     * @param direction      направление перемещения вагонов
     */
    public void moveWagons(List<Long> wagons, Long stationTrackId, WagonMovePosition direction) {

        // Получаем путь станции, на который будут перемещены вагоны
        StationTrack destinationStationTrack = stationTrackRepository.findById(stationTrackId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid destination station track ID"));

        // Инициализируем переменные для определения первого и последнего вагонов
        Wagon firstWagon = null;
        Wagon lastWagon = null;

        // Перебираем каждый идентификатор вагона из списка
        for (Long wagonId : wagons) {
            // Получаем объект вагона по его идентификатору
            Wagon wagon = wagonRepository.findById(wagonId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid wagon ID"));

            // Определяем первый вагон или вагон с позицией 0
            if (firstWagon == null || wagon.getPosition() == 0) {
                firstWagon = wagon;
            }

            // Определяем последний вагон или вагон с позицией 1
            if (lastWagon == null || wagon.getPosition() == 1) {
                lastWagon = wagon;
            }

            // Если путь станции вагона не совпадает с целевым путем,
            // изменяем путь станции на целевой путь и устанавливаем позицию вагона в начало состава
            if (!wagon.getStationTrack().equals(destinationStationTrack)) {
                wagon.setStationTrack(destinationStationTrack);
                wagon.setPosition(WagonMovePosition.HEAD);
                wagonRepository.save(wagon);
            }
        }

        // Проверяем, что первый и последний вагоны определены
        if (firstWagon == null || lastWagon == null) {
            throw new IllegalStateException("Wagons list is empty");
        }

        // Инициализируем переменную позиции в зависимости от направления перемещения
        int position = (direction == WagonMovePosition.HEAD) ? 1 : 0;

        // Перебираем каждый идентификатор вагона из списка
        for (Long wagonId : wagons) {
            // Получаем объект вагона по его идентификатору
            Wagon wagon = wagonRepository.findById(wagonId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid wagon ID"));

            // Если путь станции вагона совпадает с целевым путем,
            // устанавливаем позицию вагона в зависимости от значения переменной позиции
            if (wagon.getStationTrack().equals(destinationStationTrack)) {
                wagon.setPosition((position == 0) ? WagonMovePosition.HEAD : WagonMovePosition.TAIL);
                wagonRepository.save(wagon);

                // Обновляем значение переменной позиции
                position = (position == 0) ? 1 : 0;
            }
        }
    }

}

