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


def get_pixel_density_histogram(img):
    bottom_area = img[img.shape[0] // 3:, :]
    return np.sum(bottom_area, axis=0)


def get_normalized_pivots(img, left_pivot, right_pivot):
    midpoint = np.int(img.shape[0] / 2)
    space_between = np.int(img.shape[0] - (img.shape[0] / 3))
    most_dense = max(left_pivot, right_pivot)
    if most_dense > midpoint:
        left_pivot = right_pivot - space_between
    if most_dense < midpoint:
        right_pivot = left_pivot + space_between
    return left_pivot, right_pivot


def polynomial_curve_fit(img, rectangle_count=20, rect_width=75, rectangle_visible=False, min_pixel_density=100):
    out_img = np.dstack((img, img, img)) * 255
    rect_height = np.int(img.shape[0] / rectangle_count)

    nonzero = img.nonzero()
    nonzero_y = np.array(nonzero[0])
    nonzero_x = np.array(nonzero[1])

    pixel_density_histogram = get_pixel_density_histogram(img)

    midpoint = np.int(pixel_density_histogram.shape[0] / 2)
    left_pivot, right_pivot = get_normalized_pivots(img,
                                                    np.argmax(pixel_density_histogram[:midpoint]),
                                                    np.argmax(pixel_density_histogram[midpoint:]) + midpoint)
    left_path_index = []
    right_path_index = []

    # Step through the rectangles one by one
    for rectangle in range(rectangle_count):

        # Identify rectangle boundaries in x and y (and right and left)
        rect_y_bottom = img.shape[0] - (rectangle + 1) * rect_height
        rect_y_top = img.shape[0] - rectangle * rect_height
        rect_x_left_bottom = left_pivot - rect_width
        rect_x_left_top = left_pivot + rect_width
        rect_x_right_bottom = right_pivot - rect_width
        rect_x_right_top = right_pivot + rect_width

        # Identify the nonzero pixels in x and y within the rectangle
        left_rect_area = ((nonzero_y >= rect_y_bottom) &
                          (nonzero_y < rect_y_top) &
                          (nonzero_x >= rect_x_left_bottom) &
                          (nonzero_x < rect_x_left_top)).nonzero()[0]

        right_rect_area = ((nonzero_y >= rect_y_bottom) &
                           (nonzero_y < rect_y_top) &
                           (nonzero_x >= rect_x_right_bottom) &
                           (nonzero_x < rect_x_right_top)).nonzero()[0]

        # If you found > min_pixel_density, recenter next rectangle on their mean position
        if len(left_rect_area) > min_pixel_density:
            left_pivot, right_pivot = get_normalized_pivots(
                img, np.int(np.mean(nonzero_x[left_rect_area])), right_pivot)
        if len(right_rect_area) > min_pixel_density:
            left_pivot, right_pivot = get_normalized_pivots(
                img, left_pivot, np.int(np.mean(nonzero_x[right_rect_area])))

        # append these indices to the lists
        left_path_index.append(left_rect_area)
        right_path_index.append(right_rect_area)

        # Draw the windows on the visualization image
        if rectangle_visible:
            cv2.rectangle(out_img, (rect_x_left_bottom, rect_y_bottom), (rect_x_left_top, rect_y_top), (0, 255, 0), 2)
            cv2.rectangle(out_img, (rect_x_right_bottom, rect_y_bottom), (rect_x_right_top, rect_y_top), (0, 255, 0), 2)

    # Concatenate the arrays of indices
    left_path_index = np.concatenate(left_path_index)
    right_path_index = np.concatenate(right_path_index)

    # save image
    out_img[nonzero_y[left_path_index], nonzero_x[left_path_index]] = [0, 0, 255]
    out_img[nonzero_y[right_path_index], nonzero_x[right_path_index]] = [0, 0, 255]

    # Extract left and right line pixel positions
    left_x = nonzero_x[left_path_index]
    left_y = nonzero_y[left_path_index]
    right_x = nonzero_x[right_path_index]
    right_y = nonzero_y[right_path_index]

    # Fit a second order polynomial to each
    left_fit = np.polyfit(left_y, left_x, 2)
    right_fit = np.polyfit(right_y, right_x, 2)

    return left_fit, right_fit, out_img
