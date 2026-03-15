package co.com.iptv.model.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Channel {
    private String id;
    private String name;
    private String url;
    private String groupTitle;
    private String logo;
    private String country;
    private String tvgId;
}
