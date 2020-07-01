import sys

import airsim
from domain import CarStatusFeatures


# AirSim Car State Capture Script
# This script communicates with AirSim host and captures car state
#
# Usage:
#   python3 car_status_capture.py /path/to/feature.json airsim_hostname
#

def car_state_capture(output_filename, simulation_host):
    client = airsim.CarClient(simulation_host)
    car_state = client.getCarState()
    car_controls = client.getCarControls()
    save_features(output_filename, car_state, car_controls)


def save_features(json_filename, car_state, car_controls):
    features = \
        CarStatusFeatures(
            car_state.speed,
            car_controls.steering,
            car_controls.brake,
            car_controls.throttle)
    features.to_json(json_filename)
    print(json_filename)


def main():
    car_state_capture(sys.argv[1], sys.argv[2])


if __name__ == '__main__':
    main()
