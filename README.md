joda-time is faster than JDK on DatetimeFormat: ~5X

java -jar target/DatetimeFormatBench-1.0-SNAPSHOT-jar-with-dependencies.jar 

```
========Printing the results of net.local.test.JdkDatetime ======
	0thd throughput: 773000.0 bytes/s
	1thd throughput: 773000.0 bytes/s
	2thd throughput: 774000.0 bytes/s
	3thd throughput: 773000.0 bytes/s
Overall throughput: 773000.0 bytes/s
Overall duration: 516905(ms) total strings 399999996 bytes

========Printing the results of net.local.test.JodaDatetime ======
	0thd throughput: 3813000.0 bytes/s
	1thd throughput: 3812000.0 bytes/s
	2thd throughput: 3837000.0 bytes/s
	3thd throughput: 3834000.0 bytes/s
Overall throughput: 3824000.0 bytes/s
Overall duration: 104589(ms) total strings 399999996 bytes
```
