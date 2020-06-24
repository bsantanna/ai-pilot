import cv2
import numpy as np


# Utility function which applies distortion to image
def apply_distortion(input_image):
    image_size = (input_image.shape[1], input_image.shape[0])
    input_vertices = get_undistorted_perspective_vertices(input_image)
    output_vertices = get_distorted_perspective_vertices(input_image)
    m = cv2.getPerspectiveTransform(input_vertices, output_vertices)
    distorted = cv2.warpPerspective(input_image, m, image_size,
                                    flags=cv2.INTER_NEAREST)
    return distorted


def apply_sobel_filter(img, sx=True, sy=False, kernel_size=3, thresh=(25, 200)):
    gray = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    blur = cv2.GaussianBlur(gray, (kernel_size * 3, kernel_size * 3), 0)
    sobelx = cv2.Sobel(blur, cv2.CV_64F, 1, 0, ksize=kernel_size)
    sobely = cv2.Sobel(blur, cv2.CV_64F, 0, 1, ksize=kernel_size)

    if sx:
        abs_sobel = np.absolute(sobelx)
        scaled_sobel = np.uint8(255 * abs_sobel / np.max(abs_sobel))
    elif sy:
        abs_sobel = np.absolute(sobely)
        scaled_sobel = np.uint8(255 * abs_sobel / np.max(abs_sobel))
    else:
        mag_sobel = np.sqrt(np.square(sobelx) + np.square(sobely))
        scaled_sobel = np.uint8(255 * mag_sobel / np.max(mag_sobel))

    sxbinary = np.zeros_like(scaled_sobel)
    sxbinary[(scaled_sobel >= thresh[0]) & (scaled_sobel <= thresh[1])] = 1

    return sxbinary


def apply_canny_filter(img, kernel_size=7, thresh=(30, 75)):
    gray = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    blurred = cv2.GaussianBlur(gray, (kernel_size, kernel_size), 0)
    return cv2.Canny(blurred, thresh[0], thresh[1])


def apply_edge_filter(img):
    gray = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
    sobel = apply_sobel_filter(img)
    canny = apply_canny_filter(img)

    combined_binary = np.zeros_like(gray)
    combined_binary[(sobel == 1) | (canny == 1)] = 1
    return combined_binary


# Utility function which removes distortion from image
def remove_distortion(input_image):
    image_size = (input_image.shape[1], input_image.shape[0])
    input_vertices = get_undistorted_perspective_vertices(input_image)
    output_vertices = get_distorted_perspective_vertices(input_image)
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


# Utility function which returns undistorted perspective vertices
def get_undistorted_perspective_vertices(input_image):
    height, width = input_image.shape[:2]
    return np.float32([
        [width * 0.1640, height * 0.89722],
        [width * 0.4453, height * 0.55],
        [width * 0.5507, height * 0.55],
        [width * 0.8398, height * 0.89722]
    ])


# Utility function which returns distorted perspective vertices
def get_distorted_perspective_vertices(input_image):
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
