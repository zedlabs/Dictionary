package gre.prep.grevocabwords

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordDetailsRepository @Inject constructor(
    val jsonApi: JsonApi
) {

    suspend fun getWordDefinition(word: String) : Resource<List<DefinitionResponseItem>> {
        return handleRequest {
            withContext(Dispatchers.IO) {
                jsonApi.getWordDefinition(word)
            }
        }
    }

}