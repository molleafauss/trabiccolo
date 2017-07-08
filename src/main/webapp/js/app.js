class Application {

    init() {
        this.graph = new GraphView();
        $("#load_inbound").click({url: "/report/inbound", type: "inbound", view: this}, this.load)
        $("#load_outbound").click({url: "/report/outbound", type: "outbound", view: this}, this.load)
        $("#refresh").click({view: this}, this.reload)
        google.charts.load('current', {'packages':['corechart', 'bar']});
        google.charts.setOnLoadCallback(function() {
            $(".content").show();
        });
    }

    load(e) {
        var view = e.data.view;
        view.graph.load(e.data.url, e.data.type);
    }

    reload(e) {
        var view = e.data.view;
        view.graph.reload();
    }
}

class GraphView {
    constructor() {
        this.dom = $("#graph");
        this.current_type = null;
        this.countries = $("#countries");
        this.countries.change({view: this}, function(e) {
            e.data.view.reload();
        });
        this.chart = $("#chart_content");
    }

    reload() {
        if(this.current_type == "inbound") {
            this.load("/report/inbound", "inbound");
        } else {
            this.load("/report/outbound", "outbound");
        }
    }

    load(url, type) {
        var self = this;
        $.ajax({
            url: url,
            success: function(data) {
                self.update(type, data);
            }
        })
    }

    update(type, data) {
        console.log("Received data for " + type)
        this.current_type = type;
        var countries = this.extract_countries(data);
        this.fill_countries(countries);
        var statistics = this.filter_data(data);
        this.update_graph(statistics, type);
        this.dom.show();
    }

    extract_countries(data) {
        var result = {};
        for(let item of data) {
            result[item["country"]] = 1;
        }
        return result;
    }

    filter_data(data) {
        var country_selected = this.countries.val();
        var statistics = {};
        for(let item of data) {
            if(country_selected != "" && item["country"] != country_selected) {
                continue;
            }
            if(!(item["currency"] in statistics)) {
                statistics[item["currency"]] = 0;
            }
            statistics[item["currency"]] += item["count"];
        }
        return statistics;
    }

    fill_countries(countries) {
        this.country_selected = this.countries.val();
        this.countries.empty();
        this.countries.append($(new Option("... select a country to filter ...", "")));
        for(var i in countries) {
            var selected = this.country_selected == i;
            this.countries.append($(new Option(i, i, selected, selected)));
        }
    }

    change_country(e) {
        console.log(this);
    }

    update_graph(statistics, type) {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Currency');
        data.addColumn('number', 'Count');

        for(var c in statistics) {
            console.log(c + ": " + statistics[c]);
            data.addRow([c, statistics[c]])
        }

        var options = {
            title: 'Most transferred currencies [' + type + "]",
            hAxis: { title: 'Currency' },
            vAxis: { title: 'Count of Messages' }
        };

        this.chart.empty();
        // this.chart...?
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_content'));
        chart.draw(data, options);
    }
}
