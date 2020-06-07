import sys

import cv2
import lane_detection_image_draw_utils as draw
import matplotlib.pyplot as plt
import numpy as np


# Lane detection / image mask script
# Used to select region of interest of image
#
# Usage:
#   python3 lane_detection_image_mask.py /path/to/out.png /path/to/in.png
#

def transform_image(input_image_path, output_image_path):
    input_image = cv2.imread(input_image_path)
    output_image = cv2.cvtColor(input_image, cv2.COLOR_BGR2RGB)
    output_image = draw_warp_overlay_mask(output_image)
    plt.imsave(output_image_path, output_image)
    print(output_image_path)


def draw_warp_overlay_mask(input_image):
    output_image = draw.quadrilateral_points(input_image, get_warp_projection_vertices(input_image))
    output_image = draw.quadrilateral_lines(output_image, get_warp_projection_vertices(input_image))
    return output_image


def get_warp_projection_vertices(input_image):
    height, width = input_image.shape[:2]
    return np.float32([
        [width * 0.1640, height * 0.9722],
        [width * 0.4453, height * 0.6388],
        [width * 0.5507, height * 0.6388],
        [width * 0.8398, height * 0.9722]
    ])


def main():
    transform_image(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
