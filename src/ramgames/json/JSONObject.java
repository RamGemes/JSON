package ramgames.json;

import java.lang.constant.Constable;
import java.lang.constant.ConstantDesc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface JSONObject extends Constable {




    @Override
    default Optional<? extends ConstantDesc> describeConstable() {
        return Optional.empty();
    }
}
