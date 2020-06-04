import airsim
import sys


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
            airsim.write_file(filename, response.image_data_uint8)

    print(filename, end=" ")


if __name__ == '__main__':
    main()
