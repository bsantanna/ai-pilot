import sys

import cv2
import image_util_transform as util_transform


# Lane detection / image edge script
# Used to detect edges in a image
#
# Usage:
#   python3 image_edge.py /path/to/out.png /path/to/in.png
#

def edge_filter(input_image_path, output_image_path):
    input_image = cv2.imread(input_image_path)
    output_image = util_transform.apply_edge_filter(input_image)
    cv2.imwrite(output_image_path, output_image)
    print(output_image_path)


def main():
    edge_filter(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
