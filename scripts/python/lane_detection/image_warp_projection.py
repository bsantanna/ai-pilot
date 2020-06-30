import sys

import cv2
import image_util_draw as util_draw
import image_util_transform as util_transform


# Lane detection / image warp projection script
# Warp point projection used for boundary object detection
#
# Usage:
#   python3 image_warp_projection.py /path/to/out.png /path/to/in.png
#

def transform_image(input_image_path, output_image_path):
    input_image = cv2.imread(input_image_path)
    output_image = warp_projection_points(input_image)
    cv2.imwrite(output_image_path, output_image, [int(cv2.IMWRITE_JPEG_QUALITY), 100])
    print(output_image_path)


def warp_projection_points(input_image):
    warped_vertices = util_transform.get_warped_projection_vertices(input_image)
    output_image = util_draw.quadrilateral_points(input_image, warped_vertices)
    output_image = util_draw.quadrilateral_lines(output_image, warped_vertices)
    return output_image


def main():
    transform_image(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
