import sys

import cv2
import lane_detection_image_util_transform as util_transform


# Lane detection / image perspective script
# Used to project warp perspective from "above to ground" view
#
# Usage:
#   python3 lane_detection_image_perspective.py /path/to/out.png /path/to/in.png
#

def transform_image(input_image_path, output_image_path):
    input_image = cv2.imread(input_image_path)
    output_image = cv2.cvtColor(input_image, cv2.COLOR_BGR2RGB)
    output_image = util_transform.apply_distortion(output_image)
    cv2.imwrite(output_image_path, output_image, [int(cv2.IMWRITE_JPEG_QUALITY), 100])
    print(output_image_path)


def main():
    transform_image(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
