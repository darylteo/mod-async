load('vertx.js');

var async = org.vertx.mods.async.AsyncControllerJS.getJSModule();

vertx.logger.info('test');
vertx.logger.info(async);
async.series(
	{
		why: function(callback){
			vertx.logger.info("Series 1");
			callback.result(null, 1);
		},
		hello: function(callback){
			vertx.logger.info("Series 2");
			callback.result(null, "test");
		},
		there: function(callback){
			vertx.logger.info("Series 3");
			callback.result(null, { test : "Object" });
		},
		here: function(callback){
			vertx.logger.info("Series 4");
			callback.result(null, "blah");
		}
	},
	function(error, results){
		if(error){
			vertx.logger.info("Error:" + error);
			return;
		}
		
		vertx.logger.info(results.why);
		vertx.logger.info(results.hello);
		vertx.logger.info(results.there);
		vertx.logger.info(results.there.test);
	}
);