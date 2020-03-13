package com.portfolio.creepur.models

// imgur has everything wrapped in a stupid 'data' tag so Retrofit has to call that object specifically
// afterwards just use the 'data' property to access all of the actual Data::class properties
class DataResponse {
    var data: Data? = null
}

class DataResponseImages{
    var data: Array<DataImages?>? = null
}

class DataResponseFavorites{
    var data: Array<DataImages?>? = null
}

class DataResponseComments{
    var data: Array<DataImages?>? = null
}