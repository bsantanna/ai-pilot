import cv2
import numpy as np


# Utility function to draw points in a quadrilateral polygon
def quadrilateral_points(input_image, vertices):
    output_image = np.copy(input_image)
    color = [0, 0, 255]  # Red
    thickness = 2
    radius = 3
    x0, y0 = vertices[0]
    x1, y1 = vertices[1]
    x2, y2 = vertices[2]
    x3, y3 = vertices[3]
    cv2.circle(output_image, (x0, y0), radius, color, thickness)
    cv2.circle(output_image, (x1, y1), radius, color, thickness)
    cv2.circle(output_image, (x2, y2), radius, color, thickness)
    cv2.circle(output_image, (x3, y3), radius, color, thickness)
    return output_image


# Utility function to draw points in a quadrilateral polygon
def quadrilateral_lines(input_image, vertices):
    output_images = np.copy(input_image)
    color = [0, 0, 255]  # Red
    thickness = 2
    x0, y0 = vertices[0]
    x1, y1 = vertices[1]
    x2, y2 = vertices[2]
    x3, y3 = vertices[3]
    cv2.line(output_images, (x0, y0), (x1, y1), color, thickness)
    cv2.line(output_images, (x1, y1), (x2, y2), color, thickness)
    cv2.line(output_images, (x2, y2), (x3, y3), color, thickness)
    cv2.line(output_images, (x3, y3), (x0, y0), color, thickness)
    return output_images


