"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var TrendingComponent = (function () {
    function TrendingComponent(quoteService) {
        this.quoteService = quoteService;
    }
    TrendingComponent.prototype.ngOnInit = function () {
        this.getTrending();
    };
    TrendingComponent.prototype.getTrending = function () {
        var _this = this;
        this.isLoading = true;
        this.quoteService.getMlbTwits()
            .finally(function () {
            _this.isLoading = false;
        })
            .subscribe(function (mlbtwits) {
            _this.players = mlbtwits.trending;
            _this.playerCount = mlbtwits.players;
            _this.teamCount = mlbtwits.teams;
            _this.userCount = mlbtwits.users;
        });
    };
    TrendingComponent = __decorate([
        core_1.Component({
            selector: 'app-trending',
            templateUrl: './trending.component.html',
            styleUrls: ['./trending.component.scss']
        })
    ], TrendingComponent);
    return TrendingComponent;
}());
exports.TrendingComponent = TrendingComponent;
