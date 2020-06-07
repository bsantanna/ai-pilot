import sys


# Lane detection / image perspective script
# Used to project warp perspective from "above to ground" view
#
# Usage:
#   python3 lane_detection_image_perspective.py /path/to/out.png /path/to/in.png
#

def transform_image(input_image_path, output_image_path):
    print(input_image_path)


def main():
    transform_image(sys.argv[2], sys.argv[1])


if __name__ == '__main__':
    main()
