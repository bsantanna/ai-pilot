import sys

import cv2
import image_util_draw as util_draw
import image_util_transform as util_transform
from domain import LaneDetectionFeatures


# Lane detection / image curve fit script
# Analyses binary edge images fitting second order polynomial curves:
#  - starting from bottom to top
#  - based on pixel density pivot area
#
# Usage:
#   python3 image_curve_fit.py /path/to/out.png /path/to/features.json /path/to/edge_in.png /path/to/mask_in.png
#

def curve_fit(edge_image_path, mask_image_path, output_image_path):
    edge_image = cv2.imread(edge_image_path)
    mask_image = cv2.imread(mask_image_path)

    left_fit, right_fit, curve_image = util_draw.pixel_density_curve_fit(edge_image, rectangle_visible=True)
    curve_image = util_transform.remove_distortion(curve_image)

    output_image = cv2.addWeighted(mask_image, 1, curve_image, 0.7, 0)
    cv2.imwrite(output_image_path, output_image)
    print(output_image_path)

    return left_fit, right_fit


def main():
    left_fit, right_fit = curve_fit(sys.argv[3], sys.argv[4], sys.argv[1])
    features = LaneDetectionFeatures(left_fit, right_fit)
    json_filename = sys.argv[2]
    json_file = open(json_filename, "+w")
    json_file.write(features.to_json())
    json_file.close()


if __name__ == '__main__':
    main()
