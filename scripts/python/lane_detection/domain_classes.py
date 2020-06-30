import json


class LaneDetectionFeatures:
    def __init__(self, left_fit, right_fit):
        if left_fit is not None:
            self.left_fit = list(left_fit)

        if right_fit is not None:
            self.right_fit = list(right_fit)

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)
