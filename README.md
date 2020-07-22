# Windget - Alexandre Penczek

server.port=8080

#swagger
http://localhost:8080/swagger-ui.html#/widget-controller

# endpoints

GET
http://localhost:8080/widgets 

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

# rate limit

com.penczek.filter.RateLimitFilter - parameter: max_request