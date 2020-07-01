import sys

import airsim
import cv2


# AirSim Image Capture Script
# This script communicates with AirSim host and captures image from front camera
#
# Usage:
#   python3 image_capture.py /path/to/file.png airsim_hostname
#

def image_capture(output_filename, simulation_host):
    client = airsim.CarClient(simulation_host)
    responses = client.simGetImages([airsim.ImageRequest("1", airsim.ImageType.Scene)])

    for response in responses:
        if response.compress:
            png_filename = output_filename.split('.')[0] + '.png'
            airsim.write_file(png_filename, response.image_data_uint8)
            png_image = cv2.imread(png_filename)
            cv2.imwrite(output_filename, png_image, [int(cv2.IMWRITE_JPEG_QUALITY), 100])
            print(output_filename, end=" ")


def main():
    image_capture(sys.argv[1], sys.argv[2])


if __name__ == '__main__':
    main()
