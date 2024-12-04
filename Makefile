MVN = mvnw.cmd
RUN = spring-boot:run
TEST = test

.PHONY: all clean test run

all: run

clean:
	$(MVN) clean

test:
	$(MVN) $(TEST)

run:
	$(MVN) $(RUN)