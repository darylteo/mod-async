load('vertx.js');

var async = org.vertx.mods.async.AsyncControllerJS.getJSModule();

vertx.logger.info('test');
vertx.logger.info(async);
async.series(
	{
		why: function(){
			vertx.logger.info("1");
			return 1;
		},
		hello: function(){
			vertx.logger.info("2");
			return "TEST";
		},
		there: function(){
			vertx.logger.info("3");
			return { test : "Object" }
		}
	},
	function(result, error){
		if(error){
			vertx.logger.info("Error:" + error);
			return;
		}
		
		vertx.logger.info(result);
	}
);