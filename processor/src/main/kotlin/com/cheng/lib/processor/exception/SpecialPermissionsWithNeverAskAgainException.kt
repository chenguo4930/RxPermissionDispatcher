package com.cheng.lib.processor.exception

class SpecialPermissionsWithNeverAskAgainException
    : RuntimeException("'@NeverAskAgain' annotated method never being called with 'WRITE_SETTINGS' or 'SYSTEM_ALERT_WINDOW' permission.")