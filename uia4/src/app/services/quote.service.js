"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
require("rxjs/add/operator/map");
require("rxjs/add/operator/catch");
var core_1 = require("@angular/core");
var Observable_1 = require("rxjs/Observable");
var routes = {
    quote: function (c) { return "/jokes/random?category=" + c.category; }
};
var QuoteService = (function () {
    function QuoteService(http) {
        this.http = http;
    }
    QuoteService.prototype.getRandomQuote = function (context) {
        return this.http.get(routes.quote(context), { cache: true })
            .map(function (res) { return res.json(); })
            .map(function (body) { return body.value; })
            .catch(function () { return Observable_1.Observable.of('Error, could not load joke :-('); });
    };
    QuoteService.prototype.getMlbTwits = function () {
        return this.http.get('/mlbtwits', { cache: true })
            .map(function (res) { return res.json(); })
            .catch(function () { return Observable_1.Observable.of('Error, could not load trending players'); });
    };
    QuoteService.prototype.getTweets = function () {
        return this.http.get('/tweets', { cache: true })
            .map(function (res) { return res.json(); })
            .catch(function () { return Observable_1.Observable.of('Error, could not load tweets.'); });
    };
    QuoteService.prototype.getPlayerLabels = function () {
        return this.http.get('/playerLabels', { cache: true })
            .map(function (res) { return res.json(); })
            .catch(function () { return Observable_1.Observable.of('Error, could not load player labels'); });
    };
    QuoteService = __decorate([
        core_1.Injectable()
    ], QuoteService);
    return QuoteService;
}());
exports.QuoteService = QuoteService;
