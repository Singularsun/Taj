library(plyr)
partition <- '2016-07-21'
merged_data <- read.csv('./output/merged_data.csv',header = TRUE, sep = ",")
expection_data <- merged_data[merged_data$http.status.code >= 300 | is.na(merged_data$http.status.code >= 300) , ]

expection_data$partition.day <- paste(substr(expection_data$time.x,start = 1, stop = 10), sep = "")
expection_data$partition.hour <- paste(substr(expection_data$time.x,start = 1, stop = 13), ":00", sep = "")
by <- list(expection_data$http.status.code,
          expection_data$partition.day,
          expection_data$api)
#aggregate(x = expection_data$api, by = by, FUN = length)
expection.stats.day <- count(expection_data, c("partition.day","api","http.status.code"))
expection.stats.day[,'granularity'] <- 'day'
colnames(expection.stats.day) <- c("partition","api","code","count","granularity")

expection.stats.hour <- count(expection_data, c("partition.hour","api","http.status.code"))
expection.stats.hour[,'granularity'] <- 'hour'
colnames(expection.stats.hour) <- c("partition","api","code","count","granularity")

expection.stats <- rbind(expection.stats.day, expection.stats.hour)
file.path <- paste0("./output/expection-stats/", partition, ".csv")
write.csv(x = expection.stats, file = file.path, row.names = F)

