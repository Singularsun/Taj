library(data.table)
library(plyr)
library(sqldf)
performance_stats <- function(){
  src_data <- read.csv("output/merged_data.csv",header = TRUE, sep = ",")
  data <- src_data[c("time.x","api","process.interval")]
  #将process.interval按50ms分段
  data$interval <- data$process.interval%/%50
  #将时间转换为yyyy-mm-dd格式
  data$partition_day <- paste(substr(data$time.x, start = 1, stop = 10), sep = "")
  #将时间转换为yyyy-mm-ddThh:00格式
  data$partition_hour <- paste(substr(data$time.x,start = 1, stop = 13), ":00", sep = "")
  daily.result <- sqldf("select partition_day as partition,api,interval,count(interval) as count 
                        from data group by partition,api,interval")
  daily.result$granularity <- 'day'
  hourly.result <- sqldf("select partition_hour as partition,api,interval,count(interval) as count 
                         from data group by partition,api,interval")
  hourly.result$granularity <- 'hour'
  result = rbind(daily.result,hourly.result)
  file.path <- paste0("./output/performance-stats/", daily.result[1,'partition'], ".csv")
  write.csv(x = result, file = file.path, row.names = F)
}