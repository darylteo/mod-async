load('vertx.js');

var async = org.vertx.mods.async.AsyncControllerJS.getJSModule();

vertx.logger.info('test');
vertx.logger.info(async);
async.parallel(
	[
		function(callback){
			vertx.logger.info("Series 1");
			callback.result(null, 1);
		},
		function(callback){
			vertx.logger.info("Series 2");
			callback.result(null, "test");
		},
		function(callback){
			vertx.logger.info("Series 3");
			callback.result(null, { test : "Object" });
		}
	],
	function(error, results){
		if(error){
			vertx.logger.info("Error:" + error);
			return;
		}
		vertx.logger.info(results[2]["haha"]);
		vertx.logger.info(results[2]["test"]);
		
		results[2] = {
			"haha": "changed!"
		}
		vertx.logger.info(results[2]["haha"]);
		vertx.logger.info(results[2]["test"]);
	}
);