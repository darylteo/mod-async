load('vertx.js');

var async = org.vertx.mods.async.AsyncControllerJS.getJSModule();

vertx.logger.info('test');
vertx.logger.info(async);
async.auto(
	{
		first: function(callback){
			vertx.logger.info("Series 1");
			callback.result(null, 1);
		},
		second: function(callback){
			vertx.logger.info("Series 2");
			callback.result(null, "2");
		},
		third: ['first', 'second', function(callback){
			vertx.logger.info("Series 3");
			callback.result(null, { test : "3" });
		}],
		fourth: ['third', function(callback){
			vertx.logger.info("Series 4");
			callback.result(null, { test : "4" });
		}]
	},
	function(error, results){
		if(error){
			vertx.logger.info("Error:" + error);
			return;
		}
	}
);