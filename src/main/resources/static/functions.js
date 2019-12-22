$(document).ready(function () {
    registerSearch();
    registerPulls();
    registerTemplates();
    $("#toggle").click(function () {
        $("#twitter").toggleClass("hidden");
        $("#github").toggleClass("hidden");
    });
});

function registerSearch() {
    $("#twitter").submit(function (ev) {
        event.preventDefault();
        $.get($(this).attr('action'), {q: $("#q").val(), max: $("#max").val()}, function (data) {
            $("#resultsBlock").html(Mustache.render(templateTwitter, data));
        });
    });
}

function registerPulls() {
    $("#github").submit(function (ev) {
        event.preventDefault();
        user = $("#user").val();
        repo = $("#repo").val();
        $("#resultsBlock").html("Esperando a un nuevo pull request en " + user + "/" + repo + "...");
        $.get($(this).attr('action'), {user: user, repo: repo}, function (data) {
            alert("Ha llegado un nuevo pull request de " + data.user.login + "!");
            $("#resultsBlock").html(Mustache.render(templateGithub, data));
        });
    });
}

function registerTemplates() {
    templateTwitter = $("#template-twitter").html();
    Mustache.parse(templateTwitter);
    templateGithub = $("#template-github").html();
    Mustache.parse(templateGithub);
}
