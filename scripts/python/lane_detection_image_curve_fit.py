import sys

import cv2
import lane_detection_image_util_draw as util_draw
import lane_detection_image_util_transform as util_transform


# Lane detection / image curve fit script
# Analyses binary edge images fitting second order polynomial curves:
#  - starting from bottom to top
#  - based on pixel density pivot area
#
# Usage:
#   python3 lane_detection_image_curve_fit.py /path/to/out.png /path/to/edge_in.png /path/to/mask_in.png
#

def transform_image(edge_image_path, mask_image_path, output_image_path):
    edge_image = cv2.imread(edge_image_path)
    edge_image = cv2.cvtColor(edge_image, cv2.COLOR_RGB2GRAY)
    mask_image = cv2.imread(mask_image_path)
    left_fit, right_fit, curve_image = util_draw.pixel_density_polynomial_curve_fit(edge_image)
    curve_image = util_transform.remove_distortion(curve_image)
    output_image = cv2.addWeighted(mask_image, 1, curve_image, 0.7, 0)
    cv2.imwrite(output_image_path, output_image)
    print(output_image_path)


def main():
    transform_image(sys.argv[2], sys.argv[3], sys.argv[1])


if __name__ == '__main__':
    main()
