from picamera import PiCamera
from time import sleep

# Change this to a class later, rather than a collection of methods

# IR cut filter is off and IR LEDs are on by default.
# Change the code in boot/config.txt to change this.
def initialize_camera():
    return PiCamera()


def take_picture(camera):
    camera.start_preview()
    camera.resolution = (2592, 1944)
    sleep(5)
    camera.capture('/home/pi/rpi-sensor-app/image/night_pic.png')
    camera.stop_preview()

