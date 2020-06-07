import cv2
import numpy as np

# Utility function which applies distortion to image
def apply_distortion(input_image):
    image_size = (input_image.shape[1], input_image.shape[0])
    input_vertices = get_warped_projection_vertices(input_image)
    output_vertices = get_perspective_vertices(input_image)
    m = cv2.getPerspectiveTransform(input_vertices, output_vertices)
    distorted = cv2.warpPerspective(input_image, m, image_size,
                                    flags=cv2.INTER_NEAREST)
    return distorted

# Utility function which removes distortion from image
def remove_distortion(input_image):
    image_size = (input_image.shape[1], input_image.shape[0])
    input_vertices = get_warped_projection_vertices(input_image)
    output_vertices = get_perspective_vertices(input_image)
    m_inv = cv2.getPerspectiveTransform(output_vertices, input_vertices)
    original = cv2.warpPerspective(input_image, m_inv, image_size, flags=cv2.INTER_NEAREST)
    return original


# Utility function which returns points of projected warped projection vertices
def get_warped_projection_vertices(input_image):
    height, width = input_image.shape[:2]
    return np.float32([
        [width * 0.1640, height * 0.9722],
        [width * 0.4453, height * 0.6388],
        [width * 0.5507, height * 0.6388],
        [width * 0.8398, height * 0.9722]
    ])


# Utility function which returns points of projected perspective vertices
def get_perspective_vertices(input_image):
    height, width = input_image.shape[:2]
    width_multiplier = 0.33
    perspective_min_width = width * width_multiplier
    perspective_max_width = width - (width * width_multiplier)
    return np.float32([
        [perspective_min_width, height],
        [perspective_min_width, 0],
        [perspective_max_width, 0],
        [perspective_max_width, height]
    ])
