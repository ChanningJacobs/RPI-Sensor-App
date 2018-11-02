import RPi.GPIO as GPIO
import time
import pi_utils

inch_threshold = 2

try:
	GPIO.setmode(GPIO.BOARD)

	PIN_TRIGGER = 7
	PIN_ECHO = 11

	GPIO.setup(PIN_TRIGGER, GPIO.OUT)
	GPIO.setup(PIN_ECHO, GPIO.IN)

	GPIO.output(PIN_TRIGGER, GPIO.LOW)
	time.sleep(2)

	loop_settle = 100
	default_distance = None

	while(True):
		GPIO.output(PIN_TRIGGER, GPIO.HIGH)
		time.sleep(.01)
		GPIO.output(PIN_TRIGGER, GPIO.LOW)
		startTime = 0
		while(GPIO.input(PIN_ECHO) == 0):
			startTime = time.time()
		endTime = 0
		while(GPIO.input(PIN_ECHO) == 1):
			endTime = time.time()
		distance = ((endTime - startTime)*17150)
		distance = distance / 2.54

		if loop_settle > 0:
			loop_settle -= 1
		#print ("distance: %f" % distance)
		#print

		if loop_settle == 1:
			default_distance = distance
		if distance > default_distance + inch_threshold || distance < default_distance - inch_threshold:
			write_measurement(distance, 'image_b64_string_default', time.time())
finally:
	GPIO.cleanup()
