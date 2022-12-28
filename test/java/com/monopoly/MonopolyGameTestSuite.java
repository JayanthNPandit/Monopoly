package com.monopoly;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Monopoly Game Suite")
@SelectPackages("com.monopoly")
public class MonopolyGameTestSuite
{
}
