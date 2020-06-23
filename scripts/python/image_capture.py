import airsim
import sys
import cv2


# Image Capture Script
# This script communicates with AirSim host and captures image from front camera
#
# Usage:
#   python3 image_capture.py /path/to/file.png airsim_hostname
#
def main():
    filename = sys.argv[1]
    client = airsim.CarClient(sys.argv[2])
    responses = client.simGetImages([airsim.ImageRequest("1", airsim.ImageType.Scene)])

    for response in responses:
        if response.compress:
            png_filename = 'camera_raw.png'
            airsim.write_file(png_filename, response.image_data_uint8)
            png_image = cv2.imread(png_filename)
            cv2.imwrite(filename, png_image, [int(cv2.IMWRITE_JPEG_QUALITY), 100])
            print(filename, end=" ")


if __name__ == '__main__':
    main()
