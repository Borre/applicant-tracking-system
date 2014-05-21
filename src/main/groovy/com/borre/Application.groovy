package com.borre

import com.borre.utils.Template
import groovy.sql.Sql
import spark.Filter
import spark.Session

import static spark.Spark.*

import spark.Request
import spark.Response
import spark.Route

class Application {

    private static Map<String, String> usernamePasswords = new HashMap<String, String>()

    private static void main(String[] args) {
        Sql sql = Sql.newInstance("jdbc:mysql://127.0.0.1:3306/borre", "root", "panda")
        Template template = new Template()
        private static final String token = "w2DuRtlEOBWWjIyRM7qIs"

        usernamePasswords.put("eduardo", "123456")
        usernamePasswords.put("Lety", "123456")

        staticFileLocation("/public")

        before(new Filter("/admin/*") {
            @Override
            void handle(Request request, Response response) {
                Session session = request.session(true)
                if (session.attribute("token") == token) {
                    response.redirect("/")
                }
            }
        })

        get(new Route("/") {
            @Override
            handle(Request request, Response response) {
                return template.render("index")
            }
        })

        post(new Route("/login") {
            @Override
            handle(Request request, Response response) {
                String user = request.queryParams("user")
                String password = request.queryParams("password")
                String userExists = usernamePasswords.get(user)
                if (userExists && userExists == password) {
                    request.session().attribute("token", token)
                    response.redirect("/admin")
                } else {
                    response.redirect("/")
                }
            }
        })

        get(new Route("/admin") {
            @Override
            handle(Request request, Response response) {
                return template.render("admin/index")
            }
        })

        get(new Route("/logout") {
            @Override
            handle(Request request, Response response) {
                request.session().removeAttribute("token", "")
                response.redirect("/")
            }
        })

        get(new Route("/admin/exams") {
            @Override
            handle(Request request, Response response) {

                return template.render("admin/exams")
            }
        })
    }
}
