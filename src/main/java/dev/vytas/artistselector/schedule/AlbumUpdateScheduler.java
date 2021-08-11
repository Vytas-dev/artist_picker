package dev.vytas.artistselector.schedule;

import dev.vytas.artistselector.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlbumUpdateScheduler {

  private final UserService userService;

  /**
   * We have a limit of 100 requests per hour to the external API. Its better to implement pool /
   * debounce and only use maximum of ~50 requests / hour for albums refresh. For this simple app
   * just refresh albums daily at midnight (00:00).
   */
  @Scheduled(cron = "${app.album-refresh-trigger}")
  public void updateAlbums() {
    log.debug("Starting Top 5 album refresh for all existing artists...");
    userService.refreshAllArtistTopAlbums();
    log.debug("Album refresh completed.");
  }
}
