# Windget - Alexandre Penczek

server.port=8080

# Endpoints

GET
http://localhost:8080//widgets 

@RequestBody Filtering

GET
http://localhost:8080/widgets/{id} 

POST
http://localhost:8080/widgets

@RequestBody: Widget 

PUT
http://localhost:8080/widgets/{id}

@RequestBody: Widget
 
DELETE
http://localhost:8080/widgets/{id}
