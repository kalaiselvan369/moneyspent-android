
import com.glidotalks.android.template.TestConstants
import com.glidotalks.android.template.TestConstants.MEDIA_TYPE_APPLICATION_JSON
import com.glidotalks.android.template.common.TestDependencies.getJson
import com.glidotalks.moneyspent.github.feature.user.model.UserResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.HttpURLConnection

object MockGithubApiResponse {

    fun mockUnauthorizedResponse(): Response<UserResponse> {
        return Response.error(
            HttpURLConnection.HTTP_BAD_REQUEST,
            getJson(
                TestConstants.GET_USER_HTTP_401_JSON_FILE_PATH
            ).toResponseBody(MEDIA_TYPE_APPLICATION_JSON.toMediaType())
        )
    }
}