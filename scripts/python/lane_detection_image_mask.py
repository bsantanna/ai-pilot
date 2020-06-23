import sys

import cv2
import lane_detection_image_util_draw as util_draw
import lane_detection_image_util_transform as util_transform

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
    cv2.imwrite(output_image_path, output_image, [int(cv2.IMWRITE_JPEG_QUALITY), 100])
    print(output_image_path)


def draw_warp_overlay_mask(input_image):
    output_image = util_draw.quadrilateral_points(
        input_image, util_transform.get_warped_projection_vertices(input_image))
    output_image = util_draw.quadrilateral_lines(
        output_image, util_transform.get_warped_projection_vertices(input_image))

    return output_image


def main():
    transform_image(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
