package io.vertx.intro.first;

import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.vertx.core.json.Json;
import io.vertx.reactivex.ext.web.RoutingContext;
import java.util.NoSuchElementException;

public class ActionHelper {



    private static <T> BiConsumer<T, Throwable> writeJsonResponse(RoutingContext context, int status) {
        return (res, err) -> {
            if (err != null) {
                if (err instanceof NoSuchElementException) {
                    context.response().setStatusCode(404).end(err.getMessage());
                } else {
                    context.fail(err);
                }
            } else {
                context.response().setStatusCode(status)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(res));
            }
        };
    }

    static <T> BiConsumer<T, Throwable> ok(RoutingContext rc) {
        return writeJsonResponse(rc, 200);
    }

    static <T> BiConsumer<T, Throwable> created(RoutingContext rc) {
        return writeJsonResponse(rc, 201);
    }

    static Action noContent(RoutingContext rc) {
        return () -> rc.response().setStatusCode(204).end();
    }

    static Consumer<Throwable> onError(RoutingContext rc) {
        return err -> {
            if (err instanceof NoSuchElementException) {
                rc.response().setStatusCode(404).end(err.getMessage());
            } else {
                rc.fail(err);
            }
        };
    }

}
