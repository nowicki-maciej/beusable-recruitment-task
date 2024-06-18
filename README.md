# beusable-recruitment-task

## How to run

Requirements:

* JDK 21
* Maven 3

Start application by running:

```shell
mvn spring-boot:run
```

Run tests with:

```shell
mvn test
```

## Notes to the recruitment task

* Assumption: because it is not said directly, I assumed that the order of customer rates does not matter. Instead, I assumed that task
  should optimize profit for the hotel.
* There is no validation for the inputs, just to keep things simple.
* One of the provided test cases can't work, it is commented in the code. This is probably a mistake. Instead, I provided similar test.
* I added some more tests to cover edge and corner cases.
