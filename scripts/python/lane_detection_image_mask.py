import sys

import cv2
import matplotlib.pyplot as plt
import numpy as np


# Lane detection / image mask script
# Used to select region of interest of image
#
# Usage:
#   python3 lane_detection_image_mask.py /path/to/out.png /path/to/in.png
#

def transform_image(input_image_path, output_image_path):
    image = cv2.imread(input_image_path)
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    height = image.shape[0]
    width = image.shape[1]
    vertices = [
        (0, height),
        (width / 2, height / 4),
        (width, height)
    ]
    masked_image = apply_mask(image, np.array([vertices], np.int32))
    plt.imsave(output_image_path, masked_image)
    print(output_image_path)


def apply_mask(img, vertices):
    mask = np.zeros_like(img)
    channel_count = img.shape[2]
    match_mask_color = (255,) * channel_count
    cv2.fillPoly(mask, vertices, match_mask_color)
    masked_image = cv2.bitwise_and(img, mask)
    return masked_image


def main():
    transform_image(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
