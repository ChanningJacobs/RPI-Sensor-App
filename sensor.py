import RPi.GPIO as GPIO
import time
import pi_utils
import datetime
import camera
import base64

inch_threshold = 2

my_camera = camera.initialize_camera();

try:
	GPIO.setmode(GPIO.BOARD)

	PIN_TRIGGER = 10
	PIN_ECHO = 8

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
		if loop_settle == 1:
			default_distance = distance
		if loop_settle == 0:
			if distance > default_distance + inch_threshold or distance < default_distance - inch_threshold:
                            camera.take_picture(my_camera)

                            encoded_string = None
                            with open("./image/night_pic.png","rb") as img_file:
                                #encoded_string = img_file.read()
                                encoded_string = base64.b64encode(img_file.read())  # check for close and catch errors
                            print(encoded_string)
                            pi_utils.write_measurement(str(distance), str(encoded_string), datetime.datetime.now())
			    #print "Writing to the DB."
                            time.sleep(15)

		#print ("distance: %f" % distance)
		#print

finally:
	GPIO.cleanup()
