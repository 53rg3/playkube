package controllers;

import com.google.inject.Inject;
import models.HomeContent;
import models.TextArea;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import utils.Utils;

import java.nio.file.Path;


public class HomeController extends Controller {

    private final Form<TextArea> configForm;
    private static final Path configmapFile = Path.of("conf/configmap_file");
    private static final Path secretFile = Path.of("conf/secret_file");

    @Inject
    public HomeController(final FormFactory formFactory) {
        this.configForm = formFactory.form(TextArea.class);
    }

    public Result index() {
        final HomeContent homeContent = new HomeContent();
        homeContent.envValueConfigMap = System.getenv("CONFIG_MAP") == null ? "NOT SET" : System.getenv("CONFIG_MAP");
        homeContent.envValueSecret = System.getenv("SECRET") == null ? "NOT SET" : System.getenv("SECRET");
        homeContent.fileFromConfigMap = Utils.loadFileAsString(configmapFile);
        homeContent.fileFromSecret = Utils.loadFileAsString(secretFile);

        return ok(views.html.index.render("PlayKube", homeContent));
    }

    public Result save(final Request request) {
        final Form<TextArea> form = this.configForm.bindFromRequest(request);
        if (form.hasErrors()) {
            return badRequest("bad request");
        } else {
            if (request.uri().contains("secret")) {
                Utils.saveFile(secretFile, form.get().text);
            } else if (request.uri().contains("configmap")) {
                Utils.saveFile(configmapFile, form.get().text);
            } else {
                return badRequest("No action configured for " + request.uri());
            }
            return redirect(routes.HomeController.index());
        }
    }

}
