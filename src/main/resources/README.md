     WAGON:
 1. В таблице колонки station_track_id и waybill_id = null
И в response в Postman тоже null
 2. PUT 
Вот такой вид:
    "number": "58",
    "type": "TTG 1000",
    "tareWeight": 2000.0,
    "loadCapacity": 500.0,
    "position": 0,
    "_links": {
    "self": {
    "href": "http://localhost:8083/wagons/7"
    },
    "wagon": {
    "href": "http://localhost:8083/wagons/7"
    },
    "waybill": {
    "href": "http://localhost:8083/wagons/7/waybill"
    },
    "stationTrack": {
    "href": "http://localhost:8083/wagons/7/stationTrack"
    }
    }
    }
 3. deleteById
Удаляет, но показывает удаленную модель:
    {
    "number": "58",
    "type": "TTG 1000",
    "tareWeight": 2000.0,
    "loadCapacity": 500.0,
    "position": 0,
    "_links": {
и т.д
 4. Метод receiveWagons() - не отрабатывает
   
      CARGO
 6. deleteById()
 StationTrack
 7. POST - error
 8. get() and getAll()
 9. PUT - missing type id property 'type'
  
       WAYBILL
 10. addWagons()
 11. departWagons()

