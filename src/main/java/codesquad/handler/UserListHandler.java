package codesquad.handler;

import codesquad.database.UserDatabase;
import codesquad.file.FileReader;
import codesquad.http.HttpStatus;
import codesquad.http.request.HttpRequest;
import codesquad.http.response.HttpResponse;
import codesquad.model.User;

import java.util.List;

public class UserListHandler implements Handler {

    private final UserDatabase userDatabase = new UserDatabase();

    @Override
    public HttpResponse handle(HttpRequest request) throws Exception {
        if(request.method().equals("GET")) {
            return doGet(request);
        }
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) throws Exception {
        byte[] content = FileReader.getContent("/user/list.html");
        List<User> users = userDatabase.getAllUser();


        return new HttpResponse(HttpStatus.OK, "html", replace(new String(content, "UTF-8"), users).getBytes());
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        return new HttpResponse(HttpStatus.METHOD_NOT_ALLOWED, "text", new byte[0]);
    }

    private String replace(String html, List<User> users){
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append("""
                    <tr>
                        <td>""").append(user.getUserId()).append("</td>\n");
            sb.append("""
                    <td>""").append(user.getName()).append("</td>\n");
            sb.append("""
                    </tr>
                    """);
        }
        return html.replace("{{users}}", sb.toString());

    }
}
