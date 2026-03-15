package co.com.iptv.iptv;

import co.com.iptv.model.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class M3uParse {
    private final AtomicLong idSequence = new AtomicLong(1);
    private final Logger logger = Logger.getLogger(M3uParse.class.getName());

    public List<Channel> parse(String m3uContent) {
        String[] lines = m3uContent.split("\\R");

        List<Channel> channels = new ArrayList<>();
        String currentExtinf = null;

        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.toUpperCase().startsWith("#EXTINF")) {
                currentExtinf = line;
            } else if (!line.startsWith("#") && currentExtinf != null) {
                String url = line;
                Channel channel = buildChannelFromExtInf(currentExtinf, url);
                channels.add(channel);
                currentExtinf = null;
            }
        }

        logger.log(Level.INFO, "Parsed {0} channels from M3U content", String.join(", \n", lines[0]));
        return channels;
    }

    private Channel buildChannelFromExtInf(String extinf, String url) {
        String attrsPart = "";
        int colonIndex = extinf.indexOf(':');
        if (colonIndex >= 0 && colonIndex < extinf.length() - 1) {
            attrsPart = extinf.substring(colonIndex + 1);
        }

        String name = attrsPart;
        int lastComma = attrsPart.lastIndexOf(',');
        if (lastComma >= 0 && lastComma < attrsPart.length() - 1) {
            name = attrsPart.substring(lastComma + 1).trim();
        }

        String tvgId = extractAttr(attrsPart, "tvg-id");
        String logo = extractAttr(attrsPart, "tvg-logo");
        String group = extractAttr(attrsPart, "group-title");
        String country = extractAttr(attrsPart, "tvg-country");

        String id = String.valueOf(idSequence.getAndIncrement());

        return new Channel(
                id,
                name,
                url,
                group,
                logo,
                country,
                tvgId
        );
    }

    private String extractAttr(String attrsPart, String key) {
        String pattern = key + "=\"";
        int start = attrsPart.indexOf(pattern);
        if (start == -1) {
            return null;
        }
        start += pattern.length();
        int end = attrsPart.indexOf('"', start);
        if (end == -1) {
            return null;
        }
        return attrsPart.substring(start, end);
    }
}
