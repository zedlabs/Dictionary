package gre.prep.grevocabwords

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonApi {

    @GET("/api/v2/entries/en/{search}")
    suspend fun getWordDefinition(
        @Path("search") search: String,
    ): Response<List<DefinitionResponseItem>>
}