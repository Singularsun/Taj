library(plyr)
expection_stats <-function() {
  prepared_data <- read.csv('./output/prepared_data.csv',header = TRUE, sep = ",")
  expection_data <- prepared_data[prepared_data$http.status.code >= 300 | is.na(prepared_data$http.status.code >= 300) , ]
  if(dim(expection_data)[1] == 0) {
    return
  }
  
  partition <- expection_data$partition_day[1]
  expection.stats.day <- count(expection_data, c("partition.day","api","http.status.code"))
  expection.stats.day[,'granularity'] <- 'day'
  colnames(expection.stats.day) <- c("partition","api","code","count","granularity")
  
  expection.stats.hour <- count(expection_data, c("partition.hour","api","http.status.code"))
  expection.stats.hour[,'granularity'] <- 'hour'
  colnames(expection.stats.hour) <- c("partition","api","code","count","granularity")
  
  expection.stats <- rbind(expection.stats.day, expection.stats.hour)
  file.path <- paste0("./output/expection-stats/", partition, ".csv")
  write.csv(x = expection.stats, file = file.path, row.names = F)
}
