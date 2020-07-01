import json
import time


class FeatureProducer:
    def __init__(self):
        self.creation_timestamp = int(time.time())

    def to_json(self, filename):
        json_file = open(filename, "+w")
        json_file.write(json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4))
        json_file.close()


class CarStatusFeatures(FeatureProducer):
    def __init__(self, speed, steering, brake, throttle):
        FeatureProducer.__init__(self)
        self.speed = speed
        self.steering = steering
        self.brake = brake
        self.throttle = throttle


class LaneDetectionFeatures(FeatureProducer):
    def __init__(self, left_fit, right_fit):
        FeatureProducer.__init__(self)

        if left_fit is not None:
            self.left_fit = list(left_fit)

        if right_fit is not None:
            self.right_fit = list(right_fit)
