/**
 * Created by samuel on 15-12-30.
 */

var forbidSystem  = {

    /**
     * @Description : 将日期对象转换成字符串
     * @param obj  日期对象 包含year,month,date 属性
     */
    formatDateObjToString : function(obj){
      var year = "",
          month = "",
          day = "";
      if(obj !== undefined && obj.hasOwnProperty("year") && obj.hasOwnProperty("month") && obj.hasOwnProperty("date")){
              year = obj.year;
              month = obj.month+1;
              day = obj.date;
              return year+"-"+month+"-"+day;
      }else{
          return "";
      }
   }
}